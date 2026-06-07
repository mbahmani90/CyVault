package com.cypressit.cyvault

import android.app.Application
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.configuration.AmplifyOutputs
import com.amplifyframework.kotlin.core.Amplify
import org.koin.android.ext.koin.androidContext
import com.cypressit.cyvault.di.appModules
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration

class CyVaultApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initAmplify()
        initKoin {
            androidContext(this@CyVaultApplication)
        }
    }

    private fun initAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(AmplifyOutputs(R.raw.amplify_outputs), applicationContext)
        } catch (e: Exception) {
            android.util.Log.e("CyVault", "Failed to initialize Amplify", e)
        }
    }
}
fun initKoin(configuration: KoinAppDeclaration? = null){
    startKoin{
        configuration?.invoke(this)
        modules(appModules)
    }
}