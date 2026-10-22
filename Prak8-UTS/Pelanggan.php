<?php

class Pelanggan
{
    private $nama;
    private $noHp;

    public function __construct($nama, $noHp = "-")
    {
        $this->nama = $nama;
        $this->noHp = $noHp;
    }

    public function getNama()
    {
        return $this->nama;
    }

    public function getNoHp()
    {
        return $this->noHp;
    }
}