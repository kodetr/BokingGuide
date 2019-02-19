-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 19, 2019 at 06:27 AM
-- Server version: 10.3.12-MariaDB
-- PHP Version: 7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id8729186_db_boking`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_guide`
--

CREATE TABLE `tbl_guide` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `umur` int(5) NOT NULL,
  `agama` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `bahasa` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `kontak` int(15) NOT NULL,
  `lokasi` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `jk` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `foto` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'default.png',
  `vidio` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `rating` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `jmh_rating` int(10) NOT NULL,
  `tag` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_guide`
--

INSERT INTO `tbl_guide` (`id`, `nama`, `umur`, `agama`, `bahasa`, `kontak`, `lokasi`, `jk`, `foto`, `vidio`, `rating`, `jmh_rating`, `tag`) VALUES
(1, 'heru', 21, 'islam', 'indonesia', 87237238, 'mataram', 'perempuan', 'default.png', 'helo.png', '4', 2, 0),
(2, 'heru', 21, 'islam', 'indonesia', 87237238, 'mataram', 'perempuan', 'default.png', 'helo.png', '3', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_wisatawan`
--

CREATE TABLE `tbl_wisatawan` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `umur` int(5) NOT NULL,
  `agama` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `bahasa` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `jk` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `kontak` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `foto` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'default.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_wisatawan`
--

INSERT INTO `tbl_wisatawan` (`id`, `nama`, `umur`, `agama`, `bahasa`, `jk`, `kontak`, `foto`) VALUES
(1, 'tanwir', 21, 'islam', 'indonesia', 'Laki-Laki', '087765449987', 'default.png'),
(9, 'julainto', 45, 'Islam', 'indonesia', 'Perempuan', '081345678097', 'default.png');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_guide`
--
ALTER TABLE `tbl_guide`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_wisatawan`
--
ALTER TABLE `tbl_wisatawan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_guide`
--
ALTER TABLE `tbl_guide`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_wisatawan`
--
ALTER TABLE `tbl_wisatawan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
