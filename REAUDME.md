# Extraction de la clé privée du keystore

keytool -importkeystore -srckeystore keystore.jks -srcstorepass password -srckeypass password -srcalias saml2cert -destalias saml2cert -destkeystore keystore.p12 -deststoretype PKCS12 -deststorepass password -destkeypass password
 
openssl pkcs12 -in keystore.p12 -nodes -nocerts -out private_key.pem


# Récupération du certificat wso2 depuis son keysotre du container

# copie du keystore depuis le container vers le localhost. 463a56f1831a85223c2b5ed est id du container :
docker cp 463a56f1831a85223c2b5ed:/home/wso2carbon/wso2is-5.11.0/repository/resources/security/wso2carbon.jks .
# extraction du certificat wso2
keytool -export -keystore wso2carbon.jks -alias wso2carbon -file wso2is-certificate.cer


# Pour ne pas perdre votre config wso2, vous pouvez créer une image docker avec le container en cours d'exécution et sauvegarder ainsi toute la configuration effectuée :
docker commit wso2is_poc wso2/wso2-configure:5.11.0
# Pour démarrer cette image :
docker run --name wso2is_configure --rm -p 9443:9443 -p 9763:9763 wso2/wso2-configure:5.11.0

