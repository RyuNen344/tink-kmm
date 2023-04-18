import SwiftUI
import TinKMM

struct ContentView: View {
    private let config = TinkConfig()

	var body: some View {
        VStack {
            Text("hogehoge")
            Text(config.description)
            Text("fugafuga")
            Button("register") {
                config.register()
            }
        }

	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
