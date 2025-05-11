-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 09 mai 2025 à 18:38
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestion_bibliotheque`
--

-- --------------------------------------------------------

--
-- Structure de la table `emprunts`
--

CREATE TABLE `emprunts` (
  `id_emprunt` int(11) NOT NULL,
  `matricule_user` varchar(50) DEFAULT NULL,
  `id_livre` int(11) DEFAULT NULL,
  `date_emprunt` date DEFAULT NULL,
  `date_retour` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `emprunts`
--

INSERT INTO `emprunts` (`id_emprunt`, `matricule_user`, `id_livre`, `date_emprunt`, `date_retour`) VALUES
(4, 'U001', 1, '2025-05-09', NULL),
(5, 'U001', 2, '2025-05-09', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `livres`
--

CREATE TABLE `livres` (
  `id` int(11) NOT NULL,
  `titre` varchar(200) NOT NULL,
  `auteur` varchar(100) NOT NULL,
  `annee_publication` int(11) NOT NULL,
  `genre` varchar(50) DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT 1,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `livres`
--

INSERT INTO `livres` (`id`, `titre`, `auteur`, `annee_publication`, `genre`, `disponible`, `type`) VALUES
(1, 'Le Petit Prince', 'Antoine de Saint-Exupéry', 1943, 'Fiction', 1, 'Biographie'),
(2, '1984', 'George Orwell', 1949, 'Dystopia', 1, 'Magazine'),
(5, 'le grand maitre du taichi', 'jackie chan', 2002, 'roman', 1, NULL),
(6, 'le grand maitre du taichi', 'jackie chan', 2002, 'roman', 1, NULL),
(7, 'le grand maitre du taichi', 'jackie chan', 2002, 'roman', 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `matricule` varchar(50) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `secondname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `age` int(11) NOT NULL,
  `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`matricule`, `firstname`, `secondname`, `lastname`, `age`, `role`) VALUES
('U001', 'John', 'Doe', 'Smithen', 30, 'Member'),
('U002', 'Jane', 'Alice', 'Brown', 22, 'lecteur');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `emprunts`
--
ALTER TABLE `emprunts`
  ADD PRIMARY KEY (`id_emprunt`),
  ADD KEY `matricule_user` (`matricule_user`),
  ADD KEY `id_livre` (`id_livre`);

--
-- Index pour la table `livres`
--
ALTER TABLE `livres`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`matricule`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `emprunts`
--
ALTER TABLE `emprunts`
  MODIFY `id_emprunt` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `livres`
--
ALTER TABLE `livres`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `emprunts`
--
ALTER TABLE `emprunts`
  ADD CONSTRAINT `emprunts_ibfk_1` FOREIGN KEY (`matricule_user`) REFERENCES `users` (`matricule`),
  ADD CONSTRAINT `emprunts_ibfk_2` FOREIGN KEY (`id_livre`) REFERENCES `livres` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
