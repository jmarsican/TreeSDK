package com.javiermarsicano.tree

import com.javiermarsicano.tree.filters.AmountFilter
import com.javiermarsicano.tree.filters.CompositeFilters
import com.javiermarsicano.tree.filters.LogsFilter
import com.javiermarsicano.tree.filters.NoFilter
import com.javiermarsicano.tree.filters.PeriodRateFilter

object Tree {

    private var filter: LogsFilter

    init {
        filter = if (!BuildConfig.DEBUG) {
            NoFilter()
        } else {
            CompositeFilters().apply {
                addFilter(AmountFilter(15))
                addFilter(PeriodRateFilter(3000))
            }
        }
    }

    internal fun setup(filter: LogsFilter) {
        this.filter = filter
    }

    fun log(message: String) {
        if (filter()) {
            logDebug { message }
        }
    }
}