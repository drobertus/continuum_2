package com.mechzombie.continuum

class BoundaryType {

    String name
    String type


    static constraints = {
        name()
        type inList: ['Entry', 'Exit', 'Floating']
    }
}
