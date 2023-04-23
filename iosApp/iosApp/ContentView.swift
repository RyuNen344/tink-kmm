import SwiftUI
import TinKMM
import Tink

struct ContentView: View {
    
    private var handle: TINKKeysetHandle
    
    private let aead: Aead
    
    @State private var input: String = ""
    @State private var encrypted: Data? = nil
    @State private var decrypted: String = ""
    
    var body: some View {
        VStack {
            Group {
                Text("input")
                TextField("target", text: $input)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
            }
            
            Group {
                Text("encrypted")
                Text(encrypted?.string ?? "nil")
            }
            
            Group {
                Text("decrypted")
                Text(decrypted)
            }
            
            Group {
                Button("encrypt") {
                    encrypted = try! aead.encrypt(plaintext: input.array, associatedData: "associatedData".array).data
                }
                Button("decrypt") {
                    guard let data = encrypted else { return }
                    var result: KotlinByteArray? = nil
                    do {
                        result = try aead.decrypt(ciphertext: data.array, associatedData: "associatedData".array)
                    } catch let error {
                        print(error)
                    }
                    decrypted = result?.string ?? ""
                }
            }
        }
    }
    
    init() {
        try! AeadConfig.companion.register()
        let template = try! KeyTemplateSet.aes256Gcm.template()
        handle = try! KeysetHandleGenerator.companion.generateNew(keyTemplate: template)
        aead = try! KeysetHandleKt.getPrimitive(handle, kClass: TinkPrimitiveKt.aead) as! Aead
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

extension String {
    var array: KotlinByteArray {
        let data = Data(self.utf8)
        let result = KotlinByteArray(size: Int32(data.count))
        for (idx, byte) in data.enumerated() {
            result.set(index: Int32(idx), value: Int8(bitPattern: byte))
        }
        return result
    }
}

extension Data {
    var string: String {
        return String(decoding: self, as: UTF8.self)
    }
    
    var array: KotlinByteArray {
        let result = KotlinByteArray(size: Int32(self.count))
        for (idx, byte) in self.enumerated() {
            result.set(index: Int32(idx), value: Int8(bitPattern: byte))
        }
        return result
    }
}

extension KotlinByteArray {
    var data: Data {
        var data = Data(count: Int(size))
        for idx in 0 ..< size {
            let byte: Int8 = self.get(index: idx)
            data[Int(idx)] = UInt8(bitPattern: byte)
        }
        return data
    }
    
    var string: String {
        let size = self.size
        var data = Data(count: Int(size))
        for idx in 0 ..< size {
            let byte: Int8 = self.get(index: idx)
            data[Int(idx)] = UInt8(bitPattern: byte)
        }
        return String(decoding: data, as: UTF8.self)
    }
}
