package com.mrumagon.speedrunaa

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mrumagon.speedrunaa.databinding.ActivitySpeedRunTimerBinding
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.math.roundToInt

class SpeedRunTimer : AppCompatActivity() {

    private lateinit var binding: ActivitySpeedRunTimerBinding

    var ocarinaOfTime: MutableList<SpeedRunClass> = mutableListOf(
        SpeedRunClass("https://www.nicepng.com/png/full/161-1615542_the-great-deku-tree-is-not-creepy-because.png",
        "Enter Deku", 0, 5, false),
        SpeedRunClass("https://static.wikia.nocookie.net/zelda/images/8/81/Br%C3%BAjula_Majora%27s_Mask.png/revision/latest?cb=20110204174605&path-prefix=es",
            "Item Delay", 0, 10, false),
        SpeedRunClass("https://static.wikia.nocookie.net/zelda/images/b/b3/Matorral_Deku_OoT.png/revision/latest?cb=20120226031124&path-prefix=es",
            "Scrubs", 0, 15, false),
        SpeedRunClass("https://static.wikia.nocookie.net/zelda/images/9/9d/Reina_Gohma_OoT.png/revision/latest/scale-to-width-down/200?cb=20121110081825&path-prefix=es",
            "Gohma", 0, 30, false),
        SpeedRunClass("https://www.zeldadungeon.net/wiki/images/thumb/f/f6/Bugs-2.png/300px-Bugs-2.png",
            "Bugs", 0, 35, false),
        SpeedRunClass("https://www.models-resource.com/resources/big_icons/14/13390.png",
            "Ganondoor", 0, 46, false),
        SpeedRunClass("https://static.wikia.nocookie.net/zelda/images/1/13/Ganondorf_Artwork_3d.png/revision/latest/top-crop/width/360/height/450?cb=20110722140937&path-prefix=es",
            "Collapse", 0, 124, false),
        SpeedRunClass("https://static.wikia.nocookie.net/zelda/images/b/b9/Ganon_artwork_3d.png/revision/latest?cb=20110722154826&path-prefix=es",
        "Ganon", 0, 286, false)
    )

    var marioN64: MutableList<SpeedRunClass> = mutableListOf(
        SpeedRunClass("https://cdn02.nintendo-europe.com/media/images/08_content_images/games_6/nintendo_switch_7/nswitch_supermario3dallstars/CI_NSwitch_SuperMario3DAllStars_Mario_SuperMario64.png", "LBLJ", 0, 1.198f.toInt(), false),
        SpeedRunClass("https://www.nintenderos.com/wp-content/uploads/2019/03/bowser-super-mario-64.png", "BitDW (1)", 0, 2.438f.toInt(), false),
        SpeedRunClass("https://static.wikia.nocookie.net/fantendo/images/3/33/WhompSSB4.png/revision/latest?cb=20150407154128", "WF (4)", 0, 4.211f.toInt(), false),
        SpeedRunClass("https://static.wikia.nocookie.net/nintendo/images/f/f1/ShiftingSandLand.png/revision/latest?cb=20100821060948&path-prefix=en", "SSL (7)", 0, 6.105f.toInt(), false),
        SpeedRunClass("https://i.ytimg.com/vi/N-jBV4oCFJ4/hqdefault.jpg", "LLL (11)", 0, 8.206f.toInt(), false),
        SpeedRunClass("https://static.wikia.nocookie.net/super-mario-64-official/images/0/03/Dorrie_SM64.png/revision/latest?cb=20170305203241", "HMC (15)", 0, 10.066f.toInt(), false),
        SpeedRunClass("https://static.wikia.nocookie.net/mario/images/4/49/Sm64_mips.png/revision/latest?cb=20161206164323&path-prefix=es", "Kill", 0, 10.433f.toInt(), false),
        SpeedRunClass("https://i.blogs.es/074980/super-mario-64-mundo9-estrella1-02/450_1000.jpeg", "DDD (16)", 0, 11.254f.toInt(), false),
        SpeedRunClass("https://i.blogs.es/aa15b7/super-mario-64/1366_2000.jpg", "BitFS", 0, 12.463f.toInt(), false),
        SpeedRunClass("https://mario.wiki.gallery/images/thumb/2/29/Endless_Stairs.png/250px-Endless_Stairs.png", "pls", 0, 13.359f.toInt(), false),
        SpeedRunClass("https://mario.wiki.gallery/images/7/72/TheEnd.png", "yup", 0, 14.569f.toInt(), false)
    )

    var minecraft: MutableList<SpeedRunClass> = mutableListOf(
        SpeedRunClass("https://cdn.pixabay.com/photo/2013/07/12/19/25/minecraft-154749_1280.png", "Overworld", 0, 14.01f.toInt(), false),
        SpeedRunClass("https://static.wikia.nocookie.net/minecraft_es_gamepedia/images/6/63/Nether_%27biome%27.png/revision/latest?cb=20210522073136", "Entrar Nether", 0, 37.40f.toInt(), false),
        SpeedRunClass("https://static.wikia.nocookie.net/minecraft_gamepedia/images/2/20/Eye_of_Ender_JE1_BE1.png/revision/latest/scale-to-width-down/250?cb=20200323031219", "Buscar Portal End", 0, 55.26f.toInt(), false),
        SpeedRunClass("https://static.wikia.nocookie.net/minecraftpe/images/8/8e/Huevo_de_drag%C3%B3n_JE1_BE1.png/revision/latest?cb=20200508003250&path-prefix=es", "Matar Dragon End", 0, 57.51f.toInt(), false),
    )

    var originalList: MutableList<SpeedRunClass> = mutableListOf()
    var editableList: MutableList<SpeedRunClass> = mutableListOf()
    var newList: MutableList<SpeedRunClass> = mutableListOf()
    var resetHelper = false

    //========================================================================

    var speedRunNumber = 0;

    //Cronometro
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    //Boton DONE ayudas
    var index = 0
    var done = false
    var save = false

    //Edicion de la lista
    private lateinit var addsBtn: FloatingActionButton

    //Eliminar de la lista
    private lateinit var deletedList: SpeedRunClass

    //Actualizar de la lista
    private lateinit var updateList: SpeedRunClass
    var updateHelp: Int = 0
    var updateMode = false

    //Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapterList: SpeedRunAdapterList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeedRunTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Recoger datos de "menu_speedrun"
        val bundle = intent.extras
        val numero = bundle?.getInt("numero")
        if (numero != null) {
            speedRunNumber = numero
        }

        //Cronometro ================================
        binding.buttonSpeedRun.setOnClickListener { buttonSpeedRun() }
        binding.pause.setOnClickListener { stopTimer() }
        binding.restart.setOnClickListener { resetTimer() }

        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

        //======= Revisar en que modo entramos ================================

        if(speedRunNumber == 0){
            //Editar listas =============================
            /**ACTIVAR SOLO EN CASO DE ENTRAR DESDE "EDICION"*/
            addsBtn = findViewById(R.id.addingBtn)
            addsBtn.setOnClickListener { addInfo() }

            editableList = newList.toMutableList()

            save = true
            binding.buttonSpeedRun.text = "Guardar"
            binding.buttonSpeedRun.visibility = View.GONE

            binding.editarBttn.visibility = View.GONE
            updateMode = true

            recyclerView = findViewById(R.id.rvListaSpeedRun)
            val itemTouchHelper = ItemTouchHelper(simpleCallBack)
            itemTouchHelper.attachToRecyclerView(recyclerView)

            initRecycler()
            //=============================================
        }
        else{
            //Desactivar boton de edicion
            addsBtn = findViewById(R.id.addingBtn)
            addsBtn.setOnClickListener { addInfo() }
            addsBtn.visibility = View.GONE

            saveLists()
            initRecycler()
        }

        //=====================================================================
        //Llamar el TouchHelper

        /*
        recyclerView = findViewById(R.id.rvListaSpeedRun)
        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView) */

        //Modo Edicion
        binding.editarBttn.setOnClickListener{editButton()}

    }

    private fun editButton() {
        if(!updateMode){
            updateMode = true
            binding.editarBttn.visibility = View.GONE
            addsBtn.visibility = View.VISIBLE
            save = true
            binding.buttonSpeedRun.text = "Guardar"

            recyclerView = findViewById(R.id.rvListaSpeedRun)
            val itemTouchHelper = ItemTouchHelper(simpleCallBack)
            itemTouchHelper.attachToRecyclerView(recyclerView)

            return
        }
    }

    private var simpleCallBack = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
        ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            if(updateMode){
                var startPosition = viewHolder.adapterPosition
                var endPosition = target.adapterPosition

                Collections.swap(editableList, startPosition, endPosition)
                recyclerView.adapter?.notifyItemMoved(startPosition, endPosition)
                guardarEditableList()
            }
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition

            if(updateMode){
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        deletedList = editableList.get(position)
                        editableList.removeAt(position)
                        recyclerAdapterList.notifyItemRemoved(position)

                        Snackbar.make(recyclerView, "Tarea eliminada", Snackbar.LENGTH_LONG).setAction("Deshacer", View.OnClickListener {
                            editableList.add(position, deletedList)
                            recyclerAdapterList.notifyItemInserted(position)
                            guardarEditableList()
                        }).show()
                    }

                    ItemTouchHelper.RIGHT -> {
                        updateList = editableList.get(position)
                        updateHelp = position
                        editInfo()
                    }
                }
            }
            else{
                editableList.clear()
                editableList.addAll(originalList)
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }

    }

    private fun guardarEditableList(){
        originalList = editableList.toMutableList()
    }

    private fun editInfo(){
        val inflaterUpdate = LayoutInflater.from(this)
        val viewUpdate = inflaterUpdate.inflate(R.layout.edit_item,null)
        /**set view*/
        val urlImage = viewUpdate.findViewById<EditText>(R.id.urlImage)
        val nombreTarea = viewUpdate.findViewById<EditText>(R.id.nombreTarea)
        val tiempoEsperado = viewUpdate.findViewById<EditText>(R.id.tiempoEsperado)

        val addDialogUpdate = AlertDialog.Builder(this)
        addDialogUpdate.setView(viewUpdate)

        addDialogUpdate.setPositiveButton("Actualizar"){
                dialog,_->
            var url = updateList.icon
            val tarea = nombreTarea.text.toString()
            var tiempo: Int = 0
            //Revisar que existan valores en "tiempo" y en "URL"
            if(tiempoEsperado.text.toString().trim().length > 0){
                //Si tiene datos
                tiempo = tiempoEsperado.text.toString().toInt()
            }
            if(urlImage.text.toString().trim().length > 0){
                url = urlImage.text.toString()
            }

            editableList.set(updateHelp, SpeedRunClass(url, tarea, 0, tiempo, false))
            recyclerView.adapter!!.notifyItemChanged(updateHelp)
            dialog.dismiss()
            guardarEditableList()
            Toast.makeText(this,"Tarea actualizada",Toast.LENGTH_SHORT).show()
        }
        addDialogUpdate.setNegativeButton("Cancelar"){
                dialog,_->
            editableList.clear()
            editableList.addAll(originalList)
            recyclerView.adapter!!.notifyDataSetChanged()
            dialog.dismiss()
            Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show()
        }
        addDialogUpdate.create()
        addDialogUpdate.show()
    }

    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_item,null)
        /**set view*/
        val urlImage = v.findViewById<EditText>(R.id.urlImage)
        val nombreTarea = v.findViewById<EditText>(R.id.nombreTarea)
        val tiempoEsperado = v.findViewById<EditText>(R.id.tiempoEsperado)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)

        addDialog.setPositiveButton("Agregar"){
                dialog,_->
            var url = "https://cdn.pixabay.com/photo/2013/07/12/19/25/minecraft-154749_1280.png"
            val tarea = nombreTarea.text.toString()
            var tiempo: Int = 0
            //Revisar que existan valores en "tiempo" y en "URL"
            if(tiempoEsperado.text.toString().trim().length > 0){
                //Si tiene datos
                tiempo = tiempoEsperado.text.toString().toInt()
            }
            if(urlImage.text.toString().trim().length > 0){
                url = urlImage.text.toString()
            }

            binding.buttonSpeedRun.visibility = View.VISIBLE

            editableList.add(SpeedRunClass(url, tarea, 0, tiempo, false))
            initRecycler()
            dialog.dismiss()
            Toast.makeText(this,"Tarea agregada",Toast.LENGTH_SHORT).show()
        }
        addDialog.setNegativeButton("Cancelar"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }

    private fun resetTimer() {
        stopTimer()

        time = 0.0
        binding.txtCronometroA.text = getTimeStringFromDouble(time)
        binding.buttonSpeedRun.text = "START"

        //Desactivar botones visibles: Restart y Stop
        binding.restart.visibility = View.GONE
        binding.pause.visibility = View.GONE

        resetHelper = true
        index = 0

        //Activar editor
        binding.editarBttn.visibility = View.VISIBLE

        initRecycler()
    }

    private fun buttonSpeedRun() {
        if(save){
            originalList = editableList.toMutableList()
            binding.buttonSpeedRun.text = "START"
            addsBtn.visibility = View.GONE
            Toast.makeText(this,"¡Guardado!",Toast.LENGTH_SHORT).show()
            save = false

            //Al presionar "Guardar"
            guardarEditableList()
            updateMode = false
            binding.editarBttn.visibility = View.VISIBLE
            return
        }

        if(!done){
            if (timerStarted)
                doneActivity()
            else
                startTimer()
        }
        else{
            //reiniciar speedrun
            done = false
            resetTimer()
        }
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
        binding.buttonSpeedRun.text = "DONE"

        //Activar botones invisibles: Restart y Stop
        binding.restart.visibility = View.VISIBLE
        binding.pause.visibility = View.VISIBLE

        timerStarted = true

        //Desactivar editor
        binding.editarBttn.visibility = View.GONE

        //Activar el fondo de la primera tarea
        var valueToChange = editableList[index]
        var changeValue = SpeedRunClass(valueToChange.icon, valueToChange.actividad, valueToChange.comparacion,
                valueToChange.tiempoEsperado, true)
        editableList.set(index, changeValue)
        initRecycler()
    }

    private fun stopTimer() {
        stopService(serviceIntent)

        binding.buttonSpeedRun.text = "CONTINUE"

        //Desactivar botones visibles: Restart y Stop
        binding.restart.visibility = View.GONE
        binding.pause.visibility = View.GONE

        timerStarted = false
    }

    private fun doneActivity() {
        //Imprimir tiempo
        println("Tiempo: " + time)

        //calcular que valor poner en "comparacion"
        var comparacion = 0
        var tiempoEsperado = editableList[index].tiempoEsperado
        var tiempoAcomparar = time
        comparacion = (tiempoAcomparar - tiempoEsperado).toInt()

        //Editamos nuestra lista
        if(index < editableList.count()){
            //Toast.makeText(applicationContext, "¡SIGUE ASÍ!", Toast.LENGTH_SHORT).show()

            var valueToChange = editableList[index]
            var changeValue = SpeedRunClass(valueToChange.icon, valueToChange.actividad, comparacion, valueToChange.tiempoEsperado, false)
            editableList.set(index, changeValue)

            index += 1
            //initRecycler()

            //Activar el fondo de la siguiente tarea
            if(index < editableList.count()){
                var valueToChangeX = editableList[index]
                var changeValueX = SpeedRunClass(valueToChangeX.icon, valueToChangeX.actividad, valueToChangeX.comparacion,
                    valueToChangeX.tiempoEsperado, true)
                editableList.set(index, changeValueX)
                initRecycler()
            }
            else
                initRecycler()

            if(index == editableList.count()){
                Toast.makeText(applicationContext, "¡FELICIDADES!", Toast.LENGTH_SHORT).show()

                stopService(serviceIntent)
                binding.buttonSpeedRun.text = "Reiniciar"

                //Desactivar botones visibles: Restart y Stop
                binding.restart.visibility = View.GONE
                binding.pause.visibility = View.GONE
                done = true
            }
        }
        else{
            Toast.makeText(applicationContext, "¡Progreso Guardado!", Toast.LENGTH_SHORT).show()
            //Proceso de guardar

            binding.buttonSpeedRun.text = "Reiniciar"
            done = true
        }


    }

    //COSAS TECNICAS DEL CRONOMETRO =============================================

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.txtCronometroA.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 60 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)

    //ADAPTER DE LA LISTA ==============================================================================================

    fun initRecycler(){
        binding.rvListaSpeedRun.layoutManager = LinearLayoutManager(this)

        if (resetHelper){
            editableList = originalList.toMutableList()
            resetHelper = false
        }

        recyclerAdapterList = SpeedRunAdapterList(editableList)
        binding.rvListaSpeedRun.adapter = recyclerAdapterList

        //OLD
        /*
        val adapter = SpeedRunAdapterList(editableList)
        binding.rvListaSpeedRun.adapter = adapter */

        when (speedRunNumber) {
            0 -> {
                //MODO EDICION
                binding.titulo.text = "LISTA PERSONALIZADA"
                binding.categoria.text = "Subtitulo"
                var urlImage = "https://www.vhv.rs/dpng/d/409-4090777_thumb-image-video-game-controller-clipart-hd-png.png"
                Picasso.get().load(urlImage).into(binding.imagenTitulo)
            }

            1 -> {
                //Colocar titulo, subtitulo e imagenTitulo
                binding.titulo.text = "The Legend of Zelda: Ocarina of Time"
                binding.categoria.text = "Any%"
                var urlImage = "https://static.wikia.nocookie.net/zelda/images/8/8f/The_Legend_of_Zelda_Ocarina_of_Time.png/revision/latest?cb=20101213190903&path-prefix=es"
                Picasso.get().load(urlImage).into(binding.imagenTitulo)
            }

            2 -> {
                //Colocar titulo, subtitulo e imagenTitulo
                binding.titulo.text = "Super Mario 64"
                binding.categoria.text = "16 Stars"
                var urlImage = "https://www.pinclipart.com/picdir/middle/188-1885851_super-super-mario-64-logo-png-clipart.png"
                Picasso.get().load(urlImage).into(binding.imagenTitulo)
            }

            3 -> {
                binding.titulo.text = "Minecraft"
                binding.categoria.text = "Any%"
                var urlImage = "https://image.api.playstation.com/vulcan/img/cfn/11307uYG0CXzRuA9aryByTHYrQLFz-HVQ3VVl7aAysxK15HMpqjkAIcC_R5vdfZt52hAXQNHoYhSuoSq_46_MT_tDBcLu49I.png"
                Picasso.get().load(urlImage).into(binding.imagenTitulo)
            }
        }
    }

    fun saveLists(){
        when(speedRunNumber){
            1 -> {
                //Respaldar lista
                originalList = ocarinaOfTime.toMutableList()
                editableList = ocarinaOfTime.toMutableList()
            }
            2 -> {
                //Respaldar lista
                originalList = marioN64.toMutableList()
                editableList = marioN64.toMutableList()
            }
            3 -> {
                //Respaldar lista
                originalList = minecraft.toMutableList()
                editableList = minecraft.toMutableList()
            }
        }
    }

    //Confirmar ir para atras
    override fun onBackPressed() {

        if (timerStarted){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Deseas salir?")
            builder.setMessage("Si vuelves atras se reiniciara tu progreso.")
            builder.setPositiveButton("Si") { dialogInterface: DialogInterface, i: Int ->
                resetTimer()
                finish()
            }
            builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int -> }
            builder.show()
        }
        else{
            finish()
        }

    }
}