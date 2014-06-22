-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 04, 2012 at 08:13 PM
-- Server version: 5.5.24-log
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `thesaurus`
--
CREATE DATABASE `thesaurus` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `thesaurus`;

-- --------------------------------------------------------

--
-- Table structure for table `dictionary`
--

CREATE TABLE IF NOT EXISTS `dictionary` (
  `input_word` varchar(256) NOT NULL,
  `Value` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dictionary`
--

INSERT INTO `dictionary` (`input_word`, `Value`) VALUES
('test', ' trial,examination,experiment,assay,try,proof'),
('Brave', 'courageous,fearless,dauntless,intrepid,plucky,daring'),
('Calm', 'quiet,peaceful,still,tranquil,mild,serene,smooth,composed'),
('Dangerous', 'perilous,hazardous,risky,uncertain,unsafe'),
('Decide', 'determine,settle,choose,resolve'),
('Eager', 'keen,fervent,enthusiastic,involved,interested,alive to'),
('Explain', 'elaborate,clarify,define,interpret,justify,account for'),
('Wrong', 'incorrect,inaccurate,mistaken,erroneous,improper'),
('Valid', 'authorized,legitimate');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
