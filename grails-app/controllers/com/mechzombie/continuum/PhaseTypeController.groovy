package com.mechzombie.continuum

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PhaseTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond PhaseType.list(params), model:[phaseTypeCount: PhaseType.count()]
    }

    def show(PhaseType phaseType) {
        respond phaseType
    }

    def create() {
        respond new PhaseType(params)
    }

    @Transactional
    def save(PhaseType phaseType) {
        if (phaseType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (phaseType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond phaseType.errors, view:'create'
            return
        }

        phaseType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'phaseType.label', default: 'PhaseType'), phaseType.id])
                redirect phaseType
            }
            '*' { respond phaseType, [status: CREATED] }
        }
    }

    def edit(PhaseType phaseType) {
        respond phaseType
    }

    @Transactional
    def update(PhaseType phaseType) {
        if (phaseType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (phaseType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond phaseType.errors, view:'edit'
            return
        }

        phaseType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'phaseType.label', default: 'PhaseType'), phaseType.id])
                redirect phaseType
            }
            '*'{ respond phaseType, [status: OK] }
        }
    }

    @Transactional
    def delete(PhaseType phaseType) {

        if (phaseType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        phaseType.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'phaseType.label', default: 'PhaseType'), phaseType.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'phaseType.label', default: 'PhaseType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
