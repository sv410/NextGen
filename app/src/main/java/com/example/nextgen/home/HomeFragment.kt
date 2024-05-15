package com.example.nextgen.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.nextgen.Fragment.BaseFragment
import com.example.nextgen.Fragment.FragmentComponent
import com.example.nextgen.Fragment.FragmentScope
import com.example.nextgen.R
import javax.inject.Inject


@FragmentScope
class HomeFragment : BaseFragment() {
  @Inject
  lateinit var activity: AppCompatActivity

  @Inject
  lateinit var fragment: Fragment

  override fun injectDependencies(fragmentComponent: FragmentComponent) {
    fragmentComponent.inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(
      R.layout.fragment_home, container, false
    )
    return view
  }

  companion object {
    /** Returns instance of [HomeFragment] */
    fun newInstance(): HomeFragment {
      return HomeFragment()
    }
  }


}