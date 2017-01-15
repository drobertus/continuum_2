package com.mechzombie.continuum

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ContinuumTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ContinuumType.list(params), model:[continuumTypeCount: ContinuumType.count()]
    }

    def show(ContinuumType continuumType) {
        respond continuumType
    }

    def create() {
        //println "create params = ${params}"
        def newCT = new ContinuumType(params)
        //println "newCT parentId (1) =${newCT.parentId}"
        def parent = params.parentId
        if (parent) {
            newCT.parentId = Integer.parseInt(parent)
        }
        //println "newCT parentId (2) =${newCT.parentId}"
        respond newCT
    }

    @Transactional
    def save(ContinuumType continuumType) {

        //println ("parentId param = ${params.parentId}")
        if (continuumType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (continuumType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond continuumType.errors, view:'create'
            return
        }


        continuumType.save flush:true, failOnError: true
        def parid = params.parentId
       // println("the parid type = ${parid?.getClass()?.name}")

        if (parid){
            Integer theId = Integer.parseInt(parid)
            def parent = ContinuumType.get(theId)
         //   println "child count for $parid = ${parent.childTypes.size()}"
            parent.addToChildTypes(continuumType).save(flush: true)
         //   println "new child count for $parid = ${parent.childTypes.size()}"
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'continuumType.label', default: 'ContinuumType'), continuumType.id])
                redirect continuumType
            }
            '*' { respond continuumType, [status: CREATED] }
        }
    }

    def edit(ContinuumType continuumType) {
        respond continuumType
    }

    @Transactional
    def update(ContinuumType continuumType) {
        if (continuumType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (continuumType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond continuumType.errors, view:'edit'
            return
        }

        continuumType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'continuumType.label', default: 'ContinuumType'), continuumType.id])
                redirect continuumType
            }
            '*'{ respond continuumType, [status: OK] }
        }
    }

    @Transactional
    def delete(ContinuumType continuumType) {

        if (continuumType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        continuumType.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'continuumType.label', default: 'ContinuumType'), continuumType.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'continuumType.label', default: 'ContinuumType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
