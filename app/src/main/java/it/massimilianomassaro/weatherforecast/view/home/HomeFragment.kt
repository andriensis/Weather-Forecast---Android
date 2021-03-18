package it.massimilianomassaro.weatherforecast.view.home

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import it.massimilianomassaro.weatherforecast.R
import it.massimilianomassaro.weatherforecast.core.*
import it.massimilianomassaro.weatherforecast.core.extensions.hideKeyboard
import it.massimilianomassaro.weatherforecast.core.extensions.showAlert
import it.massimilianomassaro.weatherforecast.data.remote.model.DailyWeatherForecast
import it.massimilianomassaro.weatherforecast.databinding.HomeFragmentBinding
import it.massimilianomassaro.weatherforecast.view.daily_details.DailyWeatherFragment
import it.massimilianomassaro.weatherforecast.view.daily_details.DailyWeatherFragment.Companion.KEY_DAILY_WEATHER_FORECAST

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    private lateinit var _binding: HomeFragmentBinding
    private val binding get() = _binding

    private var filterTextHandler = Handler()
    private lateinit var screenSlidePagerAdapter: DailyWeatherPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, ::handleStateChange)
        viewModel.weatherForecastList.observe(viewLifecycleOwner, ::handleWeatherForecastChange)

        initUi()
    }

    private fun initUi() {
        screenSlidePagerAdapter = DailyWeatherPageAdapter(requireActivity())
        _binding.viewPager.adapter = screenSlidePagerAdapter

        _binding.filterEditText.doAfterTextChanged { editable ->
            filterTextHandler.removeCallbacksAndMessages(null)
            filterTextHandler.postDelayed({
                viewModel.getWeatherForecastForCity(editable.toString())
            }, 1000)
        }
    }

    private fun handleStateChange(viewModelState: ViewModelState) {
        when(viewModelState) {
            is LoadingState -> {
                binding.spinnerGroup.visibility = View.VISIBLE
                _binding.filterInputLayout.error = null
            }
            is SuccessState -> {
                binding.spinnerGroup.visibility = View.GONE
            }
            is ErrorState -> {
                binding.spinnerGroup.visibility = View.GONE

                when(viewModelState.error) {
                    is AppError.InvalidField -> _binding.filterInputLayout.error = getString(R.string.invalid_field)
                    is ServerError.NotFound -> binding.filterInputLayout.error = getString(R.string.city_not_found)
                    is NetworkNotAvailableError -> showAlert(getString(R.string.error), getString(R.string.network_error))
                    else -> showAlert(getString(R.string.error), getString(R.string.error_message_generic))
                }
            }
        }
    }

    private fun handleWeatherForecastChange(weatherForecastList: List<DailyWeatherForecast>) {
        screenSlidePagerAdapter = DailyWeatherPageAdapter(requireActivity())
        screenSlidePagerAdapter.weatherForecastList = weatherForecastList
        _binding.viewPager.adapter = screenSlidePagerAdapter
        activity?.let { hideKeyboard(it) }
    }

    private inner class DailyWeatherPageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        var weatherForecastList: List<DailyWeatherForecast> = emptyList()

        override fun getItemCount(): Int = weatherForecastList.size
        override fun createFragment(position: Int): Fragment {
            val fragment = DailyWeatherFragment()
            fragment.arguments = bundleOf(KEY_DAILY_WEATHER_FORECAST to weatherForecastList[position])
            return fragment
        }
    }
}