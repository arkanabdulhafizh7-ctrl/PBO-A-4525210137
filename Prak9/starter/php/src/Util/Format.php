<?php
declare(strict_types=1);

namespace Perpustakaan\Util;

final class Format
{
    private function __construct() { }

    public static function kolom(string $teks, int $lebar): string
    {
        return str_pad($teks, $lebar);
    }

    public static function judul(string $teks): string
    {
        return ucfirst(mb_strtolower($teks));
    }
}