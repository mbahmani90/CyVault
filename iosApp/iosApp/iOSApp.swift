import SwiftUI
import Amplify
import AWSCognitoAuthPlugin
import CyVaultApp

@main
struct iOSApp: App {

    init() {
        configureAmplify()
        registerBridge()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }

    private func configureAmplify() {
        do {
            try Amplify.add(plugin: AWSCognitoAuthPlugin())
            try Amplify.configure(with: .amplifyOutputs)
        } catch {
            print("Failed to initialize Amplify: \(error)")
        }
    }

    private func registerBridge() {
        IosAuthBridgeHolder.shared.bridge = AmplifyAuthBridge.shared
    }
}
