package com.target.targetcasestudy.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class NavDispatch
@Inject constructor(){

    private val navigationFlow = Channel<Int>(Channel.UNLIMITED)
    val navigateTo: Flow<Int>
        get() = navigationFlow.consumeAsFlow()

    public fun navigate(id: Int) {
        navigationFlow.offer(id)
    }
}