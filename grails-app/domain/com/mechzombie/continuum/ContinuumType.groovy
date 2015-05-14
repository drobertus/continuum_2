package com.mechzombie.continuum

class ContinuumType {

    String name

    BoundaryType entryBoundary
    BoundaryType exitBoundary
    def parentId
    static hasMany = [phaseTypes: PhaseType, childTypes: ContinuumType]

    static transients = ['parentId']
    static constraints = {
        name()
        entryBoundary nullable: true, blank: true
        exitBoundary nullable: true, blank: true
    }
}
