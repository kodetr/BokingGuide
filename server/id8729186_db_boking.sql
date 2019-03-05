-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 05, 2019 at 01:32 AM
-- Server version: 10.3.13-MariaDB
-- PHP Version: 7.3.2

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
-- Table structure for table `tbl_destinasi`
--

CREATE TABLE `tbl_destinasi` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `jenis` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `lokasi` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `keterangan` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `foto` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'default/default.png',
  `vidio` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_destinasi`
--

INSERT INTO `tbl_destinasi` (`id`, `nama`, `jenis`, `lokasi`, `keterangan`, `foto`, `vidio`) VALUES
(2, 'vvv', 'Air Tejun', 'g', 'g', '2.jpg', '2.mp4'),
(3, 'tt', 'Air Tejun', 'fh', 'hhb', 'default/default.png', NULL);

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
  `kontak` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `lokasi` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `jk` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `foto` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'default/default.png',
  `vidio` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rating` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `jmh_rating` int(10) NOT NULL DEFAULT 0,
  `jmh_num` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `tag` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_guide`
--

INSERT INTO `tbl_guide` (`id`, `nama`, `umur`, `agama`, `bahasa`, `kontak`, `lokasi`, `jk`, `foto`, `vidio`, `rating`, `jmh_rating`, `jmh_num`, `tag`) VALUES
(2, 'heru santo', 21, 'Islam', 'indonesia', '87237238', 'mataram', 'Pria', 'default/default.png', '2.mp4', '4.0', 7, '28.0', 0),
(6, 'tuli', 21, 'Islam', 'indo', '082686', 'mataram', 'Pria', '6.jpg', NULL, '3.5', 6, '21.0', 1);

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
  `foto` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'default/default.png',
  `nama_guide` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `tgl_mulai` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `tgl_akhir` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `tujuan_wisata` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `biaya` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(5) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_wisatawan`
--

INSERT INTO `tbl_wisatawan` (`id`, `nama`, `umur`, `agama`, `bahasa`, `jk`, `kontak`, `foto`, `nama_guide`, `tgl_mulai`, `tgl_akhir`, `tujuan_wisata`, `biaya`, `status`) VALUES
(15, 'yy', 22, 'Islam', 'uu', 'Pria', '333', 'default/default.png', 'heru santo', '5-2-2019', '30-2-2019', NULL, NULL, 0);

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
-- Indexes for table `tbl_destinasi`
--
ALTER TABLE `tbl_destinasi`
  ADD PRIMARY KEY (`id`);

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
-- AUTO_INCREMENT for table `tbl_destinasi`
--
ALTER TABLE `tbl_destinasi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_guide`
--
ALTER TABLE `tbl_guide`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tbl_wisatawan`
--
ALTER TABLE `tbl_wisatawan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
