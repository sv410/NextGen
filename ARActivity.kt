import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.Pose
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.google.firebase.database.*

data class UserLocation(
    val userId: String = "",
    val userName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

class ARActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar) // Ensure your AR layout here

        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment
        database = FirebaseDatabase.getInstance().getReference("users_location")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val userLocation = snapshot.getValue(UserLocation::class.java)
                    if (userLocation != null) {
                        addMarker(userLocation)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun addMarker(userLocation: UserLocation) {
        val pose = Pose.makeTranslation(
            userLocation.latitude.toFloat(),
            0.0f,
            userLocation.longitude.toFloat()
        )
        val anchor: Anchor = arFragment.arSceneView.session!!.createAnchor(pose)
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)

        val node = TransformableNode(arFragment.transformationSystem)
        node.setParent(anchorNode)

        ViewRenderable.builder()
            .setView(this) { context -> TextView(context).apply { id = R.id.markerTextView } }
            .build()
            .thenAccept { renderable ->
                (renderable.getView() as TextView).text = userLocation.userName
                node.renderable = renderable
            }
    }
}
