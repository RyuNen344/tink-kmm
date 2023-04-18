import SwiftUI
import TinKMM
import Tink

struct ContentView: View {
    private let config = AeadConfig()
    
    var body: some View {
        VStack {
            Text("hogehoge")
            Text(config.description)
            Text("fugafuga")
            Button("register") {
                do {
                    try config.register()
                } catch {
                    print("error")
                }
            }
        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
