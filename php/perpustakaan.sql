-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 31, 2020 at 04:23 PM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.1.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpustakaan`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` int(11) NOT NULL,
  `nama_admin` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `foto` varchar(999) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `nama_admin`, `username`, `password`, `foto`, `created_at`) VALUES
(3, 'admin', 'admin', 'admin', '', '2020-05-15 02:45:58');

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `id_anggota` int(11) NOT NULL,
  `nama_anggota` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `foto` varchar(999) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `alamat` varchar(500) NOT NULL,
  `hp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`id_anggota`, `nama_anggota`, `username`, `password`, `foto`, `created_at`, `alamat`, `hp`) VALUES
(1, 'Daffa Ammar Fauzan', 'daffa70', 'b1c358fcef275d4237c292dafa6c20a2', '', '2020-05-14 22:52:36', '', ''),
(2, 'Tes', 'tes', '28b662d883b6d76fd96e4ddc5e9ba780', '', '2020-05-18 21:17:58', '', ''),
(3, 'Andy', 'andy', 'da41bceff97b1cf96078ffb249b3d66e', '', '2020-05-31 21:11:53', 'Malang', '084122222222222');

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `id_book` int(11) NOT NULL,
  `id_anggota` int(11) NOT NULL,
  `id_buku` int(11) NOT NULL,
  `tanggal_pinjam` datetime NOT NULL,
  `tanggal_proses` timestamp NOT NULL DEFAULT current_timestamp(),
  `status_booking` varchar(50) NOT NULL,
  `denda` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`id_book`, `id_anggota`, `id_buku`, `tanggal_pinjam`, `tanggal_proses`, `status_booking`, `denda`) VALUES
(9, 1, 1, '2020-05-28 00:00:00', '2020-05-31 13:54:51', 'dibooking', 0),
(10, 1, 2, '2020-05-12 00:00:00', '2020-05-31 13:55:04', 'dibooking', 0),
(11, 1, 1, '2020-05-31 00:00:00', '2020-05-31 13:55:23', 'Sudah Kembali', 0);

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `id_buku` int(11) NOT NULL,
  `id_admin` int(11) NOT NULL,
  `judul_buku` varchar(50) NOT NULL,
  `pengarang_buku` varchar(50) NOT NULL,
  `sinopsis_buku` varchar(50) NOT NULL,
  `foto_buku` varchar(999) NOT NULL,
  `tanggal_rilisbuku` date NOT NULL,
  `tanggal_tambahbuku` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`id_buku`, `id_admin`, `judul_buku`, `pengarang_buku`, `sinopsis_buku`, `foto_buku`, `tanggal_rilisbuku`, `tanggal_tambahbuku`) VALUES
(1, 3, ' Naruto Vol.63', 'Masashi Kishimoto', 'Pertarungan terkahir di perang shinobi ke 3', 'https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/a12bbce7-b877-4538-a01a-c1c422223bed/d5pjc3b-b195fbd6-ca31-412b-b1e3-6a0e278f7bde.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3sicGF0aCI6IlwvZlwvYTEyYmJjZTctYjg3Ny00NTM4LWEwMWEtYzFjNDIyMjIzYmVkXC9kNXBqYzNiLWIxOTVmYmQ2LWNhMzEtNDEyYi1iMWUzLTZhMGUyNzhmN2JkZS5wbmcifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6ZmlsZS5kb3dubG9hZCJdfQ.sOXolpCtcH3jUVrTacJ87-6g7ZaSrTIp3qJmeOlpdfM', '2017-05-01', '2020-05-15 20:49:26'),
(2, 3, 'One Piece', 'Eiichiro Oda', 'One piece di Tanah Suci Marry', 'https://cf.shopee.co.id/file/a7dfb5e456f0d02a25e77', '2010-05-01', '2020-05-16 09:29:40');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`id_anggota`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id_book`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id_buku`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `anggota`
--
ALTER TABLE `anggota`
  MODIFY `id_anggota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `id_book` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `buku`
--
ALTER TABLE `buku`
  MODIFY `id_buku` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
