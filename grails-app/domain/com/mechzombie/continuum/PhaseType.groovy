package com.mechzombie.continuum

class PhaseType {

    String name

    String type

    static belongsTo = [continuumType: ContinuumType]
    static constraints = {

        name()
        type()
    }
}
