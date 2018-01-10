package uk.co.sentinelweb.bitwatcher.common.rx;

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class RxSchedulers constructor(// using @Inject here has an error because there are two constructors?
        override val database: Scheduler = Schedulers.single(),
        override val disk: Scheduler = Schedulers.io(),
        override val network: Scheduler = Schedulers.io(),
        override val main: Scheduler = AndroidSchedulers.mainThread()) : BwSchedulers