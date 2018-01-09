package uk.co.sentinelweb.bitwatcher.common.rx;

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

data class RxSchedulers @Inject constructor(
        override val database: Scheduler = Schedulers.single(),
        override val disk: Scheduler = Schedulers.io(),
        override val network: Scheduler = Schedulers.io(),
        override val main: Scheduler = AndroidSchedulers.mainThread()) : BwSchedulers