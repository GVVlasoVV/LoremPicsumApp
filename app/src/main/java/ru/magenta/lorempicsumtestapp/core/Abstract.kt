package ru.magenta.lorempicsumtestapp.core


sealed class Abstract {
    interface Object<T, M : Mapper> {
        fun map(mapper: M): T
    }

    interface Mapper
}