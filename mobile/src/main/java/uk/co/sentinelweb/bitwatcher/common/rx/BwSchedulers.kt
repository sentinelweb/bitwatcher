package uk.co.sentinelweb.bitwatcher.common.rx;

import io.reactivex.Scheduler

interface BwSchedulers {
    val database: Scheduler
    val disk: Scheduler
    val network: Scheduler
    val main: Scheduler
}