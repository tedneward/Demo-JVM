import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

abstract class Command {
    abstract public String command();
    abstract public String args();
    abstract public String description();
    abstract public void execute(String... args) throws Exception;
}

class GenerateSecretKey extends Command {
    public static final String ALGORITHM = "AES";
    
    public String command() { return "gensec"; }
    public String args() { return "<KEY>"; }
    public String description() { return "Generate a secret key into KEY.key"; }
    public void execute(String... args) throws Exception {
        // {{## BEGIN generate-secret-key ##}}
        // Generate secret key
        KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
        keygen.init(128);
        SecretKey aesKey = keygen.generateKey();
        
        // Store into args[0]
        try (FileOutputStream fos = new FileOutputStream(args[0] + ".key")) {
            byte[] rawKey = aesKey.getEncoded();
            fos.write(rawKey);
        }
        // {{## END generate-secret-key ##}}
    }

    public static SecretKey readSecretKeyFile(String file) throws Exception {
        // Load out of file
        try (FileInputStream fis = new FileInputStream(file + ".key")) {
            byte[] rawKey = new byte[fis.available()];
            fis.read(rawKey, 0, fis.available());
            SecretKeySpec keySpec = new SecretKeySpec(rawKey, ALGORITHM);
            return keySpec;
        }
    }
}

class EncryptWithSecretKey extends Command {
    public static final String ALGORITHM = GenerateSecretKey.ALGORITHM;

    public String command() { return "encsec"; }
    public String args() { return "<KEY> <SRC> <DEST>"; }
    public String description() { return "Encrypt SRC using secret key KEY.key writing to DEST"; }
    public void execute(String... args) throws Exception {
        String keyfile = args[0];
        String clear = args[1];
        String dest = args.length > 2 ? args[2] : "";

        SecretKey secretKey = GenerateSecretKey.readSecretKeyFile(keyfile);
        byte[] cleartext = Crypto.inSource(clear);
        // {{## BEGIN encrypt-with-secret-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherdata = cipher.doFinal(cleartext);
        // {{## END encrypt-with-secret-key ##}}
        Crypto.outDest(dest, cipherdata);
    }
}

class DecryptWithSecretKey extends Command {
    public static final String ALGORITHM = GenerateSecretKey.ALGORITHM;

    public String command() { return "decsec"; }
    public String args() { return "<KEY> <SRC> <DEST>"; }
    public String description() { return "Decrypt SRC using secret key KEY.key to DEST"; }
    public void execute(String... args) throws Exception {
        String keyfile = args[0];
        String cipherfile = args[1];
        String dest = args.length > 2 ? args[2] : "";

        byte[] ciphertext = Crypto.inSource(cipherfile);

        SecretKey secretKey = GenerateSecretKey.readSecretKeyFile(keyfile);
        // {{## BEGIN decrypt-with-secret-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plaindata = cipher.doFinal(ciphertext);
        // {{## END decrypt-with-secret-key ##}}
        Crypto.outDest(dest, plaindata);
    }
}

/*
class ... extends Command {
    public String command() { return "decryptsecret"; }
    public String args() { return ""; }
    public String description() { return ""; }
    public void execute(String... args) throws Exception {
        
    }
}
*/

class GenerateAsymmetricKey extends Command {
    public static final String ALGORITHM = "RSA";

    public String command() { return "genasy"; }
    public String args() { return "<KEYNAME>"; }
    public String description() { return "Generate a key pair; store to KEYNAME.pub and KEYNAME.prv"; }
    public void execute(String... args) throws Exception {
        // {{## BEGIN generate-keypair ##}}
        // Generate pub/priv key
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        kpg.initialize(2048);
        KeyPair pair = kpg.generateKeyPair();
        // {{## END generate-keypair ##}}

        // Store keypair into args[0].keypair
        try (FileOutputStream pairOut = new FileOutputStream(args[0] + ".keypair");
             ObjectOutputStream oos = new ObjectOutputStream(pairOut)) {
            oos.writeObject(pair);
        }
        try (FileOutputStream pubOut = new FileOutputStream(args[0] + ".pub")) {
            pubOut.write(pair.getPublic().getEncoded());
        }
        try (FileOutputStream privOut = new FileOutputStream(args[0] + ".prv")) {
            privOut.write(pair.getPrivate().getEncoded());
        }
    }

    public static PublicKey loadPublicKey(String keyfile) throws Exception {
        try (FileInputStream fis = new FileInputStream(keyfile + ".pub")) {
            byte[] raw = new byte[fis.available()];
            fis.read(raw, 0, fis.available());
            
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(raw);
            PublicKey key = keyFactory.generatePublic(keySpec);
            return key;
        }
    }
    public static PrivateKey loadPrivateKey(String keyfile) throws Exception {
        try (FileInputStream fis = new FileInputStream(keyfile + ".prv")) {
            byte[] raw = new byte[fis.available()];
            fis.read(raw, 0, fis.available());

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(raw);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            return key;
        }
    }
}
class EncryptWithPublicKey extends Command {
    public static final String ALGORITHM = GenerateAsymmetricKey.ALGORITHM;

    public String command() { return "encpub"; }
    public String args() { return "<PUBKEY> <SRC> <DEST>"; }
    public String description() { return "Encrypt SRC with public key PUBKEY.pub to DEST"; }
    public void execute(String... args) throws Exception {
        String publicKeyArg = args[0];
        String srcArg = args[1];
        String destArg = args[2];

        PublicKey publicKey = GenerateAsymmetricKey.loadPublicKey(publicKeyArg);
        byte[] cleartext = Crypto.inSource(srcArg);

        // {{## BEGIN encrypt-with-public-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherdata = cipher.doFinal(cleartext);
        // {{## END encrypt-with-public-key ##}}

        Crypto.outDest(destArg, cipherdata);
    }
}
class EncryptWithPrivateKey extends Command {
    public static final String ALGORITHM = GenerateAsymmetricKey.ALGORITHM;

    public String command() { return "encprv"; }
    public String args() { return "<PRVKEY> <SRC> <DEST>"; }
    public String description() { return "Encrypt SRC with private key PRVKEY.pub to DEST"; }
    public void execute(String... args) throws Exception {
        String privateKeyArg = args[0];
        String srcArg = args[1];
        String destArg = args[2];

        PrivateKey privateKey = GenerateAsymmetricKey.loadPrivateKey(privateKeyArg);
        byte[] cleartext = Crypto.inSource(srcArg);

        // {{## BEGIN encrypt-with-private-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] cipherdata = cipher.doFinal(cleartext);
        // {{## END encrypt-with-private-key ##}}

        Crypto.outDest(destArg, cipherdata);
    }
}
class DecryptWithPublicKey extends Command {
    public static final String ALGORITHM = GenerateAsymmetricKey.ALGORITHM;

    public String command() { return "decpub"; }
    public String args() { return "<PUBKEY> <SRC> <DEST>"; }
    public String description() { return "Decrypt SRC with public key PUBKEY.pub to DEST"; }
    public void execute(String... args) throws Exception {
        String publicKeyArg = args[0];
        String srcArg = args[1];
        String destArg = args[2];

        PublicKey publicKey = GenerateAsymmetricKey.loadPublicKey(publicKeyArg);
        byte[] cleartext = Crypto.inSource(srcArg);

        // {{## BEGIN decrypt-with-public-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] cipherdata = cipher.doFinal(cleartext);
        // {{## END decrypt-with-public-key ##}}

        Crypto.outDest(destArg, cipherdata);
    }
}
class DecryptWithPrivateKey extends Command {
    public static final String ALGORITHM = GenerateAsymmetricKey.ALGORITHM;

    public String command() { return "decprv"; }
    public String args() { return "<PRVKEY> <SRC> <DEST>"; }
    public String description() { return "Decrypt SRC with private key PRVKEY.prv to DEST"; }
    public void execute(String... args) throws Exception {
        String privateKeyArg = args[0];
        String srcArg = args[1];
        String destArg = args[2];

        PrivateKey privateKey = GenerateAsymmetricKey.loadPrivateKey(privateKeyArg);
        byte[] cleartext = Crypto.inSource(srcArg);

        // {{## BEGIN decrypt-with-private-key ##}}
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] cipherdata = cipher.doFinal(cleartext);
        // {{## END decrypt-with-private-key ##}}

        Crypto.outDest(destArg, cipherdata);
    }
}

class SignWithPrivateKey extends Command {
    public String command() { return "digsgn"; }
    public String args() { return "<PRVKEY> <SRC> <DEST>"; }
    public String description() { return "Digitally sign SRC using PRVKEY.prv to DEST"; }
    public void execute(String... args) throws Exception {
        String privateKeyArg = args[0];
        String srcArg = args[1];
        String destArg = args[2];

        byte[] cleartext = Crypto.inSource(srcArg);
        PrivateKey privateKey = GenerateAsymmetricKey.loadPrivateKey(privateKeyArg);

        // {{## BEGIN signature ##}}
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(cleartext);
        byte[] signature = privateSignature.sign();
        // {{## END signature ##}}

        Crypto.outDest(destArg, Base64.getEncoder().encode(signature));
    }
}
class VerifySignature extends Command {
    public String command() { return "verify"; }
    public String args() { return "<PUBKEY> <SRC> <SIG>"; }
    public String description() { return "Verify SRC against SIG using PUBKEY.pub"; }
    public void execute(String... args) throws Exception {
        String publicKeyArg = args[0];
        String srcArg = args[1];
        String sigArg = args[2];

        PublicKey publicKey = GenerateAsymmetricKey.loadPublicKey(publicKeyArg);
        byte[] cleartext = Crypto.inSource(srcArg);
        byte[] signatureBytes = Base64.getDecoder().decode(Crypto.inSource(sigArg));

        // {{## BEGIN verify ##}}
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(cleartext);
        if (publicSignature.verify(signatureBytes)) {
            System.out.println("Verified!");
        }
        else {
            System.out.println("NO MATCH");
        }
        // {{## END verify ##}}
    }
}


public class Crypto {

    static void outDest(String dest, byte[] data) throws Exception {
        if (dest != "") {
            try (FileOutputStream fos = new FileOutputStream(dest.substring(1))) {
                fos.write(data);
            }
        }
        else {
            System.out.write(data);
        }
    }
    static byte[] inSource(String src) throws Exception {
        byte[] data = {};
        if (src.startsWith("@")) {
            File f = new File(src.substring(1));
            int length = (int)f.length();
            data = new byte[length];
            try (FileInputStream fis = new FileInputStream(src.substring(1))) {
                fis.read(data);
            }
        }
        else {
            data = src.getBytes("UTF-8");
        }
        return data;
    }

    static List<Command> commands = Arrays.asList(
        new GenerateSecretKey(),
        new EncryptWithSecretKey(),
        new DecryptWithSecretKey(),
        new GenerateAsymmetricKey(),
        new EncryptWithPublicKey(),
        new EncryptWithPrivateKey(),
        new DecryptWithPublicKey(),
        new DecryptWithPrivateKey(),
        new SignWithPrivateKey(),
        new VerifySignature()
    );

    public static void main(String... args) throws Exception {
        if (args.length < 1) {
            usage();
            return;
        }

        String command = args[0];
        String[] argList = Arrays.asList(args).subList(1, args.length).toArray(new String[] {});
        for (Command cmd : commands) {
            if (command.equals(cmd.command())) {
                cmd.execute(argList);
                return;
            }
        }
        System.out.println("Unrecognized command: " + command);
        usage();
    }
    public static void usage() {
        System.out.println("java Crypto [command] [args...]");
        System.out.println();
        System.out.println("\twhere [commands] is one of:");
        System.out.println();
        for (Command c : commands) {
            System.out.println(c.command() + " " + c.args() + ": " + c.description());
        }
        System.out.println("... where SRC is either a quoted string or a filename prefixed with @");
        System.out.println("... and DEST is either stdout or a filename prefixed with @");
    }
}
