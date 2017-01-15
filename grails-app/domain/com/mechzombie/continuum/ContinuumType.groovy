package com.mechzombie.continuum

class ContinuumType {

    String name

    BoundaryType entryBoundary
    BoundaryType exitBoundary
    Integer parentId
   // Organization organization

    boolean isTemplate


    static hasMany = [phaseTypes: PhaseType, childTypes: ContinuumType]

    static transients = ['parentId']

    static mapping = {
        childTypes column: 'parent_id',
            joinTable: 'CHILD_CONTINUUM_TYPE_MAPPING'
    }

    static constraints = {
        name()
        entryBoundary nullable: true, blank: true
        exitBoundary nullable: true, blank: true

    }
}
