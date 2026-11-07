<?php
namespace Perpustakaan\Exception;


use Perpustakaan\Exception\PerpustakaanException;


final class BukuTidakDitemukanException extends PerpustakaanException
{
    public function __construct(private readonly string $isbn)
    {
        parent::__construct("Buku dengan ISBN {$isbn} tidak ditemukan.");
    }


    public function getIsbn(): string
    {
        return $this->isbn;
    }
}
