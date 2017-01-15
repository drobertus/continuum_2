package com.mechzombie.continuum

class Continuum {

    String name

    ContinuumType type
    //Organization organization

    static constraints = {
        name nullable: false, blank: true
        type nullable: false
      //  organization nullable: true

    }
}
