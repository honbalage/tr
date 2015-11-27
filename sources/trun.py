#
# trun
from argparse import ArgumentParser
from os.path import realpath
from shutil import rmtree
from os.path import isdir
import subprocess as sp
from os import mkdir
import json
import sys
import os


_targetdir = 'target'
_runnable_jar_home = '%s/bin' % _targetdir

def liveStream(command):
  proc = sp.Popen( command, stdout=sp.PIPE )
  while True:
    ln = proc.stdout.readline()
    if( ln=='' and proc.poll() != None ): break
    if ln: print ln.strip()
  retval = proc.poll( )
  if retval != 0:
    print '[ERROR]: %s returned non-zero exit status -- %s' % (' '.join(command), retval)
 

def argumentsOf(argv):
  parser = ArgumentParser( description='Command Line lifecycle management script for Test Reporter.' )
  parser.add_argument( '-t', '--target', type=str, default='testReporter' )
  parser.add_argument( '--clean', action='store_true' )
  parser.add_argument( '--build', action='store_true' )
  parser.add_argument( '--config-file', type=str, default='./etc/env/trun.json' \
                      ,metavar='FILE-PATH' )
  return parser.parse_args( argv )


def configOf(args):
  path = realpath( args.config_file )
  with open( path, 'rb' ) as fp:
    return json.load( fp )


def makeNonExistentJar(args, config):
  jar_path = ('%s/%s-x.jar' % ( _runnable_jar_home, args.target ))
  if args.clean:
    print '[INFO] Cleaining \'%s\'' % jar_path
    os.remove( jar_path )

  maven = [ 'mvn', 'compile', 'assembly:single' ]
  liveStream( maven )
  return jar_path


def runInJVM(jar, config):
  cmd = [ 'java', '-jar', jar, '>&0' ]
  liveStream( cmd )


def logAll(*args):
  for arg in args:
    print '[INFO] %s' % arg


def makeNonExistent(*dirs):
  for d in (dr for dr in dirs if not(isdir( dr ))):
    logAll( 'making \'%s\'' % d )
    mkdir( d )
    assert isdir( d )


def startupDB(args, config):
  db_home = realpath(config[ 'db-home' ])
  log_dir = config['db-log-dir']
  db_name = config['db-name']
  db_log_dir = '/'.join([ db_home, log_dir ])
  db_log = '/'.join([ db_log_dir, '%s.log' % db_name ])
  db = '/'.join([ db_home, db_name ])
  staging_dir = config['db-staging-dir']
  db_staging = '/'.join([ db_home, staging_dir ])
  makeNonExistent( db_home, db_log_dir, db_staging )
  #if not(isfile( db )): installDB( ... )
  rmtree( db_staging )


def main(argc, argv):
  args = argumentsOf(argv[ 1: ])
  config = configOf( args )
  jar_path = makeNonExistentJar( args, config ) 
  if not( args.build ):
    startupDB( args, config )
    runInJVM( jar_path, config )


if __name__=='__main__':
  argv = sys.argv
  argc = len(argv)
  main( argc, argv )

