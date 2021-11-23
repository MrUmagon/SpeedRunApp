package com.mrumagon.speedrunaa

import android.graphics.Color
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrumagon.speedrunaa.databinding.ItemSpeedrunlistBinding
import com.squareup.picasso.Picasso

class SpeedRunAdapterList(private val speedRunList:MutableList<SpeedRunClass>):RecyclerView.Adapter<SpeedRunAdapterList.SpeedRunHolder>(){

    override fun onBindViewHolder(holder: SpeedRunHolder, position: Int) {
        val item = speedRunList[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeedRunHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SpeedRunHolder(layoutInflater.inflate(R.layout.item_speedrunlist, parent, false))
    }

    override fun getItemCount(): Int = speedRunList.size

    class SpeedRunHolder(view: View) : RecyclerView.ViewHolder(view){

        val binding = ItemSpeedrunlistBinding.bind(view)

        fun bind(speedRunList: SpeedRunClass) = with(binding){
            binding.rvActividad.text = speedRunList.actividad
            //binding.rvTiempoA.text = speedRunList.comparacion.toString()
            //binding.rvTiempoB.text = speedRunList.tiempoEsperado.toString()
            binding.rvTiempoA.text = transformarSegundosA(speedRunList.comparacion)
            binding.rvTiempoB.text = transformarSegundosB(speedRunList.tiempoEsperado)
            Picasso.get().load(speedRunList.icon).into(binding.ivImage)
            activarFondo(speedRunList.activo)
        }

        //Activar fondo del layout padre
        fun activarFondo(activo: Boolean){
            if (activo){
                binding.layoutPadre.setBackgroundColor("#BC2554B5".toColor())
            }
            else
                binding.layoutPadre.setBackgroundColor(0)
        }

        //Testeo para convertir segundos a formato de tiempo ==================================

        fun transformarSegundosA(time: Int): String{
            var minutes = time % 86400 % 3600 / 60
            var seconds = time % 86400 % 60 % 60

            if(time < 0){
                minutes *= -1
                seconds *= -1
                binding.rvTiempoA.setTextColor("#54F72F".toColor()) //Cambiar a verde
                return makeTimeStringNegative(minutes, seconds)
            }
            else if(time == 0){
                return makeTimeString(minutes, seconds)
            }
            else if(time > 0)
                binding.rvTiempoA.setTextColor("#F72F41".toColor()) //Cambiar a rojo
                return makeTimeStringPositive(minutes, seconds)

        }

        fun transformarSegundosB(time: Int): String{
            val minutes = time % 86400 % 3600 / 60
            val seconds = time % 86400 % 60 % 60
            return makeTimeString(minutes, seconds)
        }

        private fun makeTimeString(min: Int, sec: Int): String = String.format("%2d:%02d", min, sec)

        private fun makeTimeStringNegative(min: Int, sec: Int): String = String.format("-%2d:%02d", min, sec)

        private fun makeTimeStringPositive(min: Int, sec: Int): String = String.format("+%2d:%02d", min, sec)

        fun String.toColor(): Int = Color.parseColor(this)
    }

}