package ar.edu.unahur.obj2.caralibro

class Usuario {
  var publicaciones = mutableListOf<Publicacion>()

  var amigos = mutableListOf<Usuario>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun puedeVerPublicacion(publicacion: Publicacion) : Boolean {
    return (publicacion.permisos == Permisos.PUBLICA
            || (publicacion.permisos == Permisos.EXCLUIDOS && !publicacion.listaExcluidos.contains(this))
            || (publicacion.permisos == Permisos.PERMITIDOS && publicacion.listaPermitidos.contains(this)))
  }



  fun agregarAmigo(usuarioAmigo: Usuario) {
    amigos.add(usuarioAmigo)
  }

  fun masAmistosoQue(otroUsuario : Usuario) = this.amigos.size > otroUsuario.amigos.size
}
