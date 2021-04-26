package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

var FACTOR_COMPRESION : Double = 0.7

abstract class Publicacion {

  var likes : Int = 0
  private var listaPersonasALasQueLesGusta = mutableListOf<Usuario>()
  var listaPermitidos = mutableListOf<Usuario>()
  var listaExcluidos = mutableListOf<Usuario>()
  var permisos : Permisos = Permisos.PUBLICA
  var propietario : Usuario? = null

  abstract fun espacioQueOcupa(): Int

  fun recibirMeGusta(usuario: Usuario){
    if(!listaPersonasALasQueLesGusta.contains(usuario)){
      likes+=1
      listaPersonasALasQueLesGusta.add(usuario)
    }
  }

  fun personasALasQueLesGusta() = this.listaPersonasALasQueLesGusta

  fun cantidadDeMeGustas() = this.likes

  fun agregarPermisos(permiso : Permisos, listaPersonas : MutableList<Usuario> = mutableListOf()){

    if(permiso == Permisos.EXCLUIDOS && listaPersonas.isNotEmpty()){
      for(usuario in listaPersonas){
        listaExcluidos.add(usuario)
      }

    }
    if(permiso == Permisos.PERMITIDOS && listaPersonas.isNotEmpty()){
      for(usuario in listaPersonas){
        listaPermitidos.add(usuario)
      }
    }
    this.permisos = permiso
  }

  fun recibioLikesDe(usuario: Usuario) = this.listaPersonasALasQueLesGusta.contains(usuario)
}

class Foto(private val alto: Int, private val ancho: Int) : Publicacion() {

  override fun espacioQueOcupa() = ceil(alto * ancho * FACTOR_COMPRESION).toInt()

}

class Texto(private val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(private val duracion: Int, var calidad: Calidad) : Publicacion() {

  override fun espacioQueOcupa() = duracion * calidad.aplicarCalidad()

  fun cambiarCalidad(calidadACambiar: Calidad) {
    this.calidad = calidadACambiar
  }

}

fun cambiarFactorDeCompresion(nuevoFactor: Double){
  FACTOR_COMPRESION = nuevoFactor
}
