package me.ks.chan.material.symbols.ksp.repository

interface ProcessRepository<T, R> {
    fun process(unprocessed: T): R
}

infix fun <T, R> T.processWith(processRepository: ProcessRepository<T, R>): R {
    return processRepository.process(this)
}
