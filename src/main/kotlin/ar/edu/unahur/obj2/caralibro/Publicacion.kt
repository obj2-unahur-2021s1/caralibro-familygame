package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {

  var meGustas : Int = 0

  var listaPersonasALasQueLesGusta = mutableListOf<Usuario>()

  abstract fun espacioQueOcupa(): Int

  fun recibirMeGusta(usuario: Usuario){
    if(!listaPersonasALasQueLesGusta.contains(usuario)){
      meGustas+=1
      listaPersonasALasQueLesGusta.add(usuario)
    }
  }

  fun personasALasQueLesGusta() = this.listaPersonasALasQueLesGusta

  fun cantidadDeMeGustas() = this.meGustas
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  val factorDeCompresion = 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val duracion: Int, var calidad: Calidad) : Publicacion() {

  override fun espacioQueOcupa() = duracion * calidad.aplicarCalidad()

  fun cambiarCalidad(calidadACambiar: Calidad) {
    this.calidad = calidadACambiar
  }

}
