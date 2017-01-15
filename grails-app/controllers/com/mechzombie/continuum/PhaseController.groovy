package com.mechzombie.continuum

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PhaseController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Phase.list(params), model:[phaseCount: Phase.count()]
    }

    def show(Phase phase) {
        respond phase
    }

    def create() {
        respond new Phase(params)
    }

    @Transactional
    def save(Phase phase) {
        if (phase == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (phase.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond phase.errors, view:'create'
            return
        }

        phase.save (flush:true, failOnError: true)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'phase.label', default: 'Phase'), phase.id])
                redirect phase
            }
            '*' { respond phase, [status: CREATED] }
        }
    }

    def edit(Phase phase) {
        respond phase
    }

    @Transactional
    def update(Phase phase) {
        if (phase == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (phase.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond phase.errors, view:'edit'
            return
        }

        phase.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'phase.label', default: 'Phase'), phase.id])
                redirect phase
            }
            '*'{ respond phase, [status: OK] }
        }
    }

    @Transactional
    def delete(Phase phase) {

        if (phase == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        phase.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'phase.label', default: 'Phase'), phase.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'phase.label', default: 'Phase'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
