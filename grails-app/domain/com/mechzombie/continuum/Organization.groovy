package com.mechzombie.continuum

/**
 * Created by David on 1/14/2017.
 */
class Organization {

    String name
    Organization parentOrg
    static constraints = {
        name()
        parentOrg nullable: true, blank: true


    }

}
