# SAE-3.01-D-veloppement-d-application
ASSAL Hugo
BOURDON Marin
DELCOURT Mathias
GRANDJEAN Mathéo
KONÉ Ismaël

Bilan v1 :
Nous avons ajoutés les fonctionnalités : 
  Ajouter des listes.
  Création d'une ébauche d'interface

En plus de ces fonctionnalités nous avons commencé codés plusieurs classes. En utilisant le patron Composite, pour les taches, afin de pouvoir par la suite faire des taches dépendantes. Ainsi que le patron architectural MVC.
Lors de cette itération, nous nous sommes rendus compte, que nous nous étions mal organisés, pour ce qui est de la planification des fonctionnalités à faire chaque itérations. Après avoir pris en compte ce problème nous avons refais notre plannification des itérations, en cherchant à avoir un planning le plus réaliste possible.

Pour lancer l'application il faut se rendre dans la classe MyApplication et lancer la méthode main.


Bilan v2 :
Lors de cette itération nous avons implémenter les fonctionnalités suivantes :
- Créer des tâches
- Sélectionner des tâches
- Modifier une liste
- Supprimer une liste

Nous avons aussi rendu le tableau des listes déroulant, dans le cas où le nombre de listes étaient supérieurs à la taille de la fenêtre. 
Nous avons aussi décidé de réorganiser notre dossier src/ en utilisant notamment des packages rendant le dossier plus clair. 
De plus, avec ce nouveau planning d’itération, nous sommes mieux organisés et assignons plus facilement chaque tâche à chacun. 
Le temps pris pour refaire notre planning, nous a pris une grande partie de notre itération, ce qui a nous a poussé à coder directement et réaliser certains diagrammes à posteriori. 
Cependant dès la troisième itération, nous allons reprendre correctement la conception de notre projet et des prochaines itérations.


Bilan v3 :
Lors de cette itération nous avons implémenter les fonctionnalités suivantes : 
- Modifier une tâche
- Attribuer une date de début et de fin à une tâche
- Attribuer des sous-tâches
- Archiver une tâche
- Modifier un tableau
- Supprimer un tableau

Nous avons aussi commencer à la fonctionnalité pour déplacer les tâches, elle fonctionne partiellement mais provoque encore quelques
erreurs qui seront corrigées lors de l'itération 4. En dehors de ça, d'un point de vue organisation, les tâches ont bien été reparties
entre chaque membre et chacun savait clairement ce qu'il devait faire. Nous avons fait un effort pour réaliser correctement nos différents
diagrammes en temps voulu, afin de réaliser une itération des plus propres possibles.

Pour lancer l'application, il faut utiliser le main de la classe MyApplication.


Bilan v4 :
Durant cette itération nous avons commencé les fonctionnalités de base de données supplémentaires aux fonctionnalités demandées. 
Nous avons ainsi à régler quelques détails pour la fonctionnalité de génération de diagramme de Gantt qui sera certainement fini en itération 5.
Durant l'itération 4 nous avons aussi créer et implémenter l'interface de connexion / d'incription qui sera reliée à la base de données pour pouvoir accéder à l'application ensuite,
ce qui inclut la grande avancée de notre base de données et donc du patron active record qui seront totalement implémenté en itération 5.
Enfin la possibilité de déplacer une tâche avec un système de clic and dropped n'est pas encore finalisé totalement et sera aussi implémenté en itération 5.

Finalement nous n'avons pas finalisé l'implémentation de plusieurs des fonctionnalités que nous avions prévu pour cette itération mais celle-ci sont bien avancées voire presque fini pour certaines.


Bilan v5 : 
Lors de cette itération, nous avons principalement poursuivi les fonctionnalités commencées dans les itérations précédentes, plus
particulièrement les suivantes : 
  - Diagramme de Gantt
  - Drag N Drop
  - Implémentation d'activeRecord et de la BDD

Cependant, 2 fonctionnalités ont malgré tout était additionnées au reste :
  - Choix d'un Template

Cette fonctionnalité implique la création au préalable de ces Templates. Notre objectif est de terminer les 3 fonctionnalités citées
précédemments d'ici la fin de la 6ème itération. Ces dernières fonctionnalités sont complexes et demande un grand nombre de modification
du code, expliquant aussi pourquoi elles demandent plusieurs itérations afin d'être réalisées.


Bilan v6 :
Lors de cette itération nous nous sommes rendus compte qu'il existait une meilleure façon d'utiliser les templates, Hugo a donc légèrement modifié ce que Mathéo avait fini sans utiliser le Patron Stratégie
