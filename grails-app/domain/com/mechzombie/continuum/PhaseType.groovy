package com.mechzombie.continuum

class PhaseType {

    String name
    String type
    int order

    Organization organization

    PhaseType mustFollow
    PhaseType mustProceed

    static belongsTo = [continuumType: ContinuumType]
    static constraints = {

        name()
        type()
    }
}
