import SwiftUI
import Tink

struct ContentView: View {
	var body: some View {
        VStack {
            Text("hogehoge")
            Text(TINKConfig().description)
            Text("fugafuga")
        }
        
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
