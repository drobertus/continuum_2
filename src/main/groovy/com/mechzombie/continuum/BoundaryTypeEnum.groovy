package com.mechzombie.continuum


enum BoundaryTypeEnum {

    Entry,
    Exit,
    Floating

    static List<String> getBoundaryTypeList() {
        def list = new ArrayList<String>()
        BoundaryTypeEnum.values().each{
            list << it.name();
            println 'adding ' + it.name()
        }
        return list
    }

}