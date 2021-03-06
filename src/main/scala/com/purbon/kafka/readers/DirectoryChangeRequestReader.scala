package com.purbon.kafka.readers

import java.io.File

import com.purbon.kafka.parsers.{ChangeRequest, ChangeRequestParser}

object DirectoryChangeRequestReader {

  def apply(directory:String, parser: ChangeRequestParser): DirectoryChangeRequestReader = {
    val dir = new File(directory)
    val iterator = new FSMigrationReaderIterator(dir.listFiles()
                                                    .sortBy(_.getAbsolutePath)
                                                    .iterator, parser)
    new DirectoryChangeRequestReader(iterator)
  }
}

class DirectoryChangeRequestReader(var fileSystemMigrationIterator: FSMigrationReaderIterator) extends ChangeRequestReader {

  def this(directory: String, parser: ChangeRequestParser) {
    this(new FSMigrationReaderIterator(new File(directory)
      .listFiles()
      .sortBy(_.getAbsolutePath)
      .iterator, parser))
  }

  override def iterator: Iterator[ChangeRequest] = fileSystemMigrationIterator
}
