package it.massimilianomassaro.weatherforecast.view.daily_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import it.massimilianomassaro.weatherforecast.R
import it.massimilianomassaro.weatherforecast.core.extensions.showAlert
import it.massimilianomassaro.weatherforecast.core.extensions.toFormattedString
import it.massimilianomassaro.weatherforecast.data.remote.model.DailyWeatherForecast
import it.massimilianomassaro.weatherforecast.databinding.DailyWeatherFragmentBinding
import it.massimilianomassaro.weatherforecast.databinding.HomeFragmentBinding

class DailyWeatherFragment : Fragment() {

    private lateinit var _binding: DailyWeatherFragmentBinding
    private val binding get() = _binding

    private lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DailyWeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<DailyWeatherForecast>(KEY_DAILY_WEATHER_FORECAST)?.let {
            initUi(it)
        } ?: run {
            showAlert(getString(R.string.error), getString(R.string.error_message_generic))
        }
    }

    private fun initUi(dailyWeatherForecast: DailyWeatherForecast) {
        hourlyWeatherAdapter = HourlyWeatherAdapter(
            dailyWeatherForecast.hourlyWeatherForecastList
        )
        _binding.hourlyForecastRecyclerView.adapter = hourlyWeatherAdapter
    }

    companion object {
        const val KEY_DAILY_WEATHER_FORECAST = "KEY_DAILY_WEATHER_FORECAST"
    }
}