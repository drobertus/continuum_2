package com.mechzombie.continuum

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ContinuumController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Continuum.list(params), model:[continuumCount: Continuum.count()]
    }

    def show(Continuum continuum) {
        respond continuum
    }

    def create() {
        respond new Continuum(params)
    }

    @Transactional
    def save(Continuum continuum) {
        if (continuum == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (continuum.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond continuum.errors, view:'create'
            return
        }

        continuum.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'continuum.label', default: 'Continuum'), continuum.id])
                redirect continuum
            }
            '*' { respond continuum, [status: CREATED] }
        }
    }

    def edit(Continuum continuum) {
        respond continuum
    }

    @Transactional
    def update(Continuum continuum) {
        if (continuum == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (continuum.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond continuum.errors, view:'edit'
            return
        }

        continuum.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'continuum.label', default: 'Continuum'), continuum.id])
                redirect continuum
            }
            '*'{ respond continuum, [status: OK] }
        }
    }

    @Transactional
    def delete(Continuum continuum) {

        if (continuum == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        continuum.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'continuum.label', default: 'Continuum'), continuum.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'continuum.label', default: 'Continuum'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
