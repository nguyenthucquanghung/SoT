package vnd.macro.sot.di

import dagger.Component
import vnd.macro.sot.model.RefLinksService
import vnd.macro.sot.view.LoginActivity
import vnd.macro.sot.viewmodel.ListViewModel

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: RefLinksService) {}
    fun inject(viewModel: ListViewModel) {}
    fun inject(loginActivity: LoginActivity) {}
}