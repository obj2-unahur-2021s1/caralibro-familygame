package ar.edu.unahur.obj2.caralibro

enum class Calidad {
    SD {
        override fun aplicarCalidad() = 1
       },
    HD720p{
        override fun aplicarCalidad() = 3
          },
    HD1080p{
        override fun aplicarCalidad() = 5
    };

    abstract fun aplicarCalidad() : Int
}