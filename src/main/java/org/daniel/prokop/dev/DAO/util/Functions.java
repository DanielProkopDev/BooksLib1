package org.daniel.prokop.dev.DAO.util;

import org.daniel.prokop.dev.DAO.AbstractEntity;

import java.util.Comparator;

public class Functions {

        public static Comparator<AbstractEntity> COMPARATOR_BY_ID = Comparator.comparing(AbstractEntity::getId);

}
