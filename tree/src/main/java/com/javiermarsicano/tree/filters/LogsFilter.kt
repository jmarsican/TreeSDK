package com.javiermarsicano.tree.filters

interface LogsFilter {
    operator fun invoke(): Boolean
}

class NoFilter: LogsFilter {
    override fun invoke() = true
}

class CompositeFilters : LogsFilter {
    var filters = mutableListOf<LogsFilter>()

    fun addFilter(filter: LogsFilter) {
        filters.add(filter)
    }

    override fun invoke(): Boolean {
        return filters.firstOrNull { !it.invoke() } == null
    }

}

class PeriodRateFilter(private val intervalInMillis: Long): LogsFilter {
    private var lastTimestamp: Long = System.currentTimeMillis()

    override fun invoke(): Boolean {
        val currentTimestamp = System.currentTimeMillis()

        if (currentTimestamp - lastTimestamp > intervalInMillis) {
            lastTimestamp = currentTimestamp
            return true
        }
        return false
    }
}

class AmountFilter(private val max: Long): LogsFilter {
    private var totalLogs: Long = 0

    override fun invoke(): Boolean {
        if (totalLogs < max) {
            totalLogs++
            return true
        }
        return false
    }
}