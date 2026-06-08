import Foundation
import Amplify
import AWSCognitoAuthPlugin
import CyVaultApp

@objc public class AmplifyAuthBridge: NSObject, IosAuthBridge {

    @objc public static let shared = AmplifyAuthBridge()

    public func signIn(
        email: String,
        password: String,
        completion: @escaping (String?, String?, String?) -> Void
    ) {
        Task {
            do {
                let result = try await Amplify.Auth.signIn(username: email, password: password)
                if result.isSignedIn {
                    let attributes = try await Amplify.Auth.fetchUserAttributes()
                    let sub = attributes.first(where: { $0.key == .sub })?.value ?? ""
                    let name = attributes.first(where: { $0.key == .name })?.value ?? ""
                    completion(sub, name, nil)
                } else {
                    completion(nil, nil, "Sign in failed: additional steps required")
                }
            } catch {
                completion(nil, nil, error.localizedDescription)
            }
        }
    }

    public func register(
        name: String,
        email: String,
        password: String,
        completion: @escaping (String?) -> Void
    ) {
        Task {
            do {
                let userAttributes = [
                    AuthUserAttribute(.email, value: email),
                    AuthUserAttribute(.name, value: name)
                ]
                let options = AuthSignUpRequest.Options(userAttributes: userAttributes)
                _ = try await Amplify.Auth.signUp(username: email, password: password, options: options)
                completion(nil)
            } catch {
                completion(error.localizedDescription)
            }
        }
    }

    public func confirmSignUp(
        email: String,
        code: String,
        completion: @escaping (String?) -> Void
    ) {
        Task {
            do {
                _ = try await Amplify.Auth.confirmSignUp(for: email, confirmationCode: code)
                completion(nil)
            } catch {
                completion(error.localizedDescription)
            }
        }
    }

    public func forgotPassword(
        email: String,
        completion: @escaping (String?) -> Void
    ) {
        Task {
            do {
                _ = try await Amplify.Auth.resetPassword(for: email)
                completion(nil)
            } catch {
                completion(error.localizedDescription)
            }
        }
    }

    public func signOut(completion: @escaping (String?) -> Void) {
        Task {
            _ = await Amplify.Auth.signOut()
            completion(nil)
        }
    }

    public func signInWithGoogle(
        completion: @escaping (String?, String?, String?, String?) -> Void
    ) {
        Task {
            do {
                _ = try await Amplify.Auth.signInWithWebUI(for: .google, presentationAnchor: UIApplication.shared.windows.first!)
                let attributes = try await Amplify.Auth.fetchUserAttributes()
                let sub = attributes.first(where: { $0.key == .sub })?.value ?? ""
                let name = attributes.first(where: { $0.key == .name })?.value ?? ""
                let email = attributes.first(where: { $0.key == .email })?.value ?? ""
                completion(sub, name, email, nil)
            } catch {
                completion(nil, nil, nil, error.localizedDescription)
            }
        }
    }

    public func getCurrentUser(
        completion: @escaping (String?, String?, String?, String?) -> Void
    ) {
        Task {
            do {
                let session = try await Amplify.Auth.fetchAuthSession()
                if !session.isSignedIn {
                    completion(nil, nil, nil, nil)
                    return
                }
                let attributes = try await Amplify.Auth.fetchUserAttributes()
                let sub = attributes.first(where: { $0.key == .sub })?.value ?? ""
                let name = attributes.first(where: { $0.key == .name })?.value ?? ""
                let email = attributes.first(where: { $0.key == .email })?.value ?? ""
                completion(sub, name, email, nil)
            } catch {
                completion(nil, nil, nil, error.localizedDescription)
            }
        }
    }
}
