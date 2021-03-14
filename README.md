Fonctionnement rapide de l’Application

En accord avec le cahier des charges nous avons dû mettre en place plusieurs outils permettant de remplir nos objectifs. Pour rappel nous avons reçu un fichier JSON avec une liste d’informations concernant les arrêts de bus avec lesquels nous devons permettre une interaction.
Pour commencer après avoir récupéré les informations du JSON et les avoirs Parser a l’aide de Retrofit pour en faire des informations utilisables, nous avons repartis ces informations sous la forme d’un ArrayList<> permettant le déplacement des listes d’une activité à l’autre. 

Nous avons donc créé une première Activité sous la forme d’un RecyclerView, permettant un display récursif d’une vue xml en fonction du contenu du JSON que nous avons parser au préalable. Nous pouvons grâce à ce système afficher tous informations souhaitées comme par exemple pour nous, le nom de la station, ainsi que les lignes de bus avec lesquelles l’arrêt fonctionne. Ceci nous permet donc d’assurer une future compatibilité avec tous changement de contenu apporté a fichier JSON. 

Concernant le détail d’un arrêt de bus, il est considéré comme une nouvelle view, avec un nouvel xml dédié. Ce qui nous permet de lire ou de sauvegarder une photo ou plusieurs avec comme référence son ID dans la liste de stations. Nous offrant la possibilité de récupérer uniquement les photos correspondantes sans risques de mixer les différentes photos des différentes stations.

Evidemment je ne vais pas détailler l’ensemble des fonctionnalités de notre application. Pour faire court nous avons donc à notre disposition, un système de capture d’image. En demandant les droits d’utilisation de la caméra, ainsi que du stockage interne, nous pouvons permettre a l’utilisateur de prendre une photo depuis l’appareil photo, et ainsi de la sauvegarder, en y incluant un titre que l’utilisateur choisi, ainsi que la date a laquelle la dit photo a été prise. Pour une recherche pratique et rapide de notre photo, nous avons utilisé le système d’uri que android nous propose, donnant accès à la photo que nous souhaitons. Ceci nous permet de la réinjecter dans le détail de la station concerner. Nous avons donc grâce a ce système une bonne compartimentation de nos données et de l’utilisation de celle-ci.

Concernant la partie MAP du projet, nous avons utilisé l’API de google map, nous permettant de display des markers a des endroits que nous avons choisi. En effet en bouclant sur notre liste de donnée parser depuis le JSON, nous pouvons extraire les latitudes et les longitudes de chacune des stations. De ce fait, nous pouvons donnée à l’api une infinité de marker a placer pour nous. Ce procédé est plutôt trivial, mais surtout il nous laisse une grande marge de manouvre concernant la personnalisation de la map. En effet si vous le souhaiter nous pouvons apporter nous même un type de marker, les modifier par Marker points, créant ainsi un action Button pour chacun des markers. Ce qui a terme nous permettrait de faire une liaison entre les points de la map et une view particulier si besoin est.
