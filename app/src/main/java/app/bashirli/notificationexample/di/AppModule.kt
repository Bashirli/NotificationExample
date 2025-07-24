package app.bashirli.notificationexample.di

import android.app.NotificationManager
import android.content.Context
import app.bashirli.notificationexample.service.NotificationHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created on 24.07.25.
 * @author Sahib Bashirli
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun injectLiveNotification(
        @ApplicationContext context : Context,
        notificationManager : NotificationManager
    ) = NotificationHandler(
        context,notificationManager
    )

    @Provides
    @Singleton
    fun injectNotificationManager(
        @ApplicationContext context : Context
    ) = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

}