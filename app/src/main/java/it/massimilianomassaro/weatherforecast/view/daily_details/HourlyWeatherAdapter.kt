package it.massimilianomassaro.weatherforecast.view.daily_details

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import it.massimilianomassaro.weatherforecast.R
import it.massimilianomassaro.weatherforecast.core.extensions.formatNoDigits
import it.massimilianomassaro.weatherforecast.data.remote.model.HourlyWeatherForecast
import it.massimilianomassaro.weatherforecast.data.remote.model.formatDateTime
import it.massimilianomassaro.weatherforecast.data.remote.model.getWeatherIconUrl

class HourlyWeatherAdapter(
    private var list: List<HourlyWeatherForecast>
) : RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourlyWeatherForecast: HourlyWeatherForecast = list[position]
        holder.bind(hourlyWeatherForecast)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.hourly_forecast_item, parent, false)) {
        private var hourTextView: TextView = itemView.findViewById(R.id.hourTextView)
        private var descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private var temperatureTextView: TextView = itemView.findViewById(R.id.temperatureTextView)
        private var weatherImageView: ImageView = itemView.findViewById(R.id.weatherImageView)

        fun bind(hourlyWeatherForecast: HourlyWeatherForecast) {
            hourTextView.text = hourlyWeatherForecast.formatDateTime()
            descriptionTextView.text = hourlyWeatherForecast.weather.first().description

            val temperatureString = "${itemView.context.getString(R.string.temperature)}: ${hourlyWeatherForecast.main.temp.formatNoDigits()} °C"
            temperatureTextView.text = temperatureString

            Picasso.get()
                .load(hourlyWeatherForecast.getWeatherIconUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(weatherImageView)
        }
    }
}

