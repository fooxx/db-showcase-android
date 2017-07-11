package cz.koto.misak.dbshowcase.android.mobile.utility

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

sealed class ApplicationEvent {
    object StateUpdateRequest : ApplicationEvent()
}

val applicationEvents: Subject<ApplicationEvent> = PublishSubject.create<ApplicationEvent>().toSerialized()